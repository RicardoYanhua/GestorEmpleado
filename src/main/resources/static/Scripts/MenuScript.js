function ToggleMenuAction() {
	const menuContainer = document.getElementById('Menu-container');
	const toggleButton = document.getElementById('ToggleMenuBtn');
	const textLogoHidden = document.getElementById('TextLogo');
	toggleButton.classList.toggle('MenuBtnToggleRotate');
	const ishidden = menuContainer.classList.toggle('MenuHidden');
	textLogoHidden.classList.toggle('HiddenLogo');
	localStorage.setItem('menuIsHidden', ishidden);
}

function setActiveByIndex(index) {
	const menuLists = document.querySelectorAll('.MenuItems');
	menuLists.forEach(menuList => {
		const navLinks = menuList.querySelectorAll('.MenuButton');
		navLinks.forEach(link => link.classList.remove('MenuButtonActived'));
		if (index >= 0 && index < navLinks.length) {
			navLinks[index].classList.add('MenuButtonActived');
		}
	});
}

function checkWindowSize() {
	const modalElement = document.getElementById('overlayPanel');


	const modal = bootstrap.Modal.getInstance(modalElement);
	if (window.innerWidth > 1180 && modal._isShown) {
		modal.hide();
	}
}

let OneSuccess = false;
function checkWindowSizeforMenu() {
    const isCollapsed = localStorage.getItem('menuIsHidden') === 'true';

	 if(window.innerWidth < 1380){
		OneSuccess = false;
	 }
    if (window.innerWidth < 1380 && !isCollapsed) {
        ToggleMenuAction();
		
    }else if(window.innerWidth > 1380 && isCollapsed && !OneSuccess){
		ToggleMenuAction();
		OneSuccess = true;
	}

    
}

window.addEventListener('resize', checkWindowSizeforMenu);

window.addEventListener('resize', checkWindowSize);

window.addEventListener('load', checkWindowSize);

document.addEventListener('DOMContentLoaded', () => {
	const MenuContainer = document.getElementById('Menu-container');
	const toggleButton = document.getElementById('ToggleMenuBtn');
	const textLogoHidden = document.getElementById('TextLogo');

	const isCollapsed = localStorage.getItem('menuIsHidden') === 'true';


	if (isCollapsed) {
		MenuContainer.classList.add('MenuHidden');
		toggleButton.classList.add('MenuBtnToggleRotate');
		textLogoHidden.classList.add('HiddenLogo');
	} else {
		MenuContainer.classList.remove('MenuHidden');
		toggleButton.classList.remove('MenuBtnToggleRotate');
		textLogoHidden.classList.remove('HiddenLogo');
	}
});





