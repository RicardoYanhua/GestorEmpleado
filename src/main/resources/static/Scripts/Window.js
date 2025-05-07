
//Eliminacion de foto en localStorage
if (window.location.pathname.includes("/Empleado/Lista")) {
	Object.keys(sessionStorage).forEach(function(key) {
		if (key.startsWith("fotoPreview-")) {
			sessionStorage.removeItem(key);
		}
	});
}