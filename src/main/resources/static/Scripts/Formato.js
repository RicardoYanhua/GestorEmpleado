
function FormatoDNI(event) {
	const input = event.target;
	input.value = input.value.replace(/[^0-9]/g, '').slice(0, 8);
}

function FormatoNumerosEnteros(event) {
	const input = event.target;
	input.value = input.value.replace(/[^0-9]/g, '');
}

function FormatoNumerosDecimales(event) {
	const input = event.target;
	let value = input.value;
	value = value.replace(/[^0-9.]/g, '');
	const partes = value.split('.');
	if (partes.length > 2) {
		value = partes[0] + '.' + partes[1];
	}
	if (partes.length === 2) {
		partes[1] = partes[1].substring(0, 2);
		value = partes[0] + '.' + partes[1];
	}
	input.value = value;
}

document.addEventListener('DOMContentLoaded', () => {
	const inputs = document.querySelectorAll('input[type="text"]');

	inputs.forEach(input => {
		input.addEventListener('focus', function () {
			this.select();
		});
	});
});


function FormatoTelefono(event) {
	let input = event.target.value.replace(/\D/g, '');
	if (input.length > 9) {
		input = input.slice(0, 9);
	}
	let formattedInput = input.replace(/(\d{3})(?=\d)/g, '$1 ');
	event.target.value = formattedInput;
}

function FormatoSoloLetrasCapital(event) {
	let input = event.target.value;
	input = input.replace(/[^a-zA-ZáéíóúÁÉÍÓÚñÑ\s]/g, '');

	input = input
		.toLowerCase()
		.replace(/\b\w/g, char => char.toUpperCase());

	event.target.value = input;
}

function AplicarFormtatos() {

	const IntegerInputs = document.querySelectorAll('.FormatoInteger');
	IntegerInputs.forEach(input => {
		input.addEventListener('input', FormatoNumerosEnteros);
	});

	const DoubleInputs = document.querySelectorAll('.FormatoDouble');
	DoubleInputs.forEach(input => {
		input.addEventListener('input', FormatoNumerosDecimales);
	});

	const DNIInputs = document.querySelectorAll('.FormatoDni');
	DNIInputs.forEach(input => {
		input.addEventListener('input', FormatoDNI);
	});

	const TelefonoInputs = document.querySelectorAll('.FormatoTelefono');
	TelefonoInputs.forEach(input => {
		input.addEventListener('input', FormatoTelefono);
	});
	
	const TextoInputs = document.querySelectorAll('.FormatoTextoCapital');
		TextoInputs.forEach(input => {
			input.addEventListener('input', FormatoSoloLetrasCapital);
		});
}


document.addEventListener('DOMContentLoaded', AplicarFormtatos);