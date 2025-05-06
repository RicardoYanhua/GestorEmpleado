function ToggleVisibilityPassword(button) {
	const input = document.getElementById("inputPassword");
	if (input.type === "password") {
		input.type = "text";
	} else {
		input.type = "password";
	}
	button.classList.toggle("showPassword");
}