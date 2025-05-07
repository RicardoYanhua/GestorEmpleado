const fileInput = document.getElementById("InputImageFile");
const previewImage = document.getElementById("previewImage");
const empCodigo = previewImage.dataset.empCodigo;
const storageKey = `fotoPreview-${empCodigo}`;

fileInput.addEventListener("change", function() {
	const file = this.files[0];
	if (file && file.type.startsWith("image/")) {
		const reader = new FileReader();
		reader.onload = function(e) {
			previewImage.src = e.target.result;
			sessionStorage.setItem(storageKey, e.target.result);
		};
		reader.readAsDataURL(file);
	}
});


window.addEventListener("load", function() {
	const savedImage = sessionStorage.getItem(storageKey);
	if (savedImage) {
		previewImage.src = savedImage;
	}
});