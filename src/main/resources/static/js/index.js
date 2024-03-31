








/*


window.onload = function() {

	const form = document.getElementById('form');

	form.addEventListener('submit', e => {
		const name = document.getElementById("name").value;
		const email = document.getElementById("email").value;
		const pass1 = document.getElementById("password1").value;
		const pass2 = document.getElementById("password2").value;
		
		const reg =/^[^\s@]+@[^\s@]+\.[^\s@]+$/;

		if (name.trim() == "" || name.length < 3) {
			
			e.preventDefault();
			
			const error = document.getElementById("nameerror");
			error.style.display = 'inline';
			error.style.margin = 0;
			error.style.color = 'red';
			error.innerHTML = 'Enter a valid name'
		}
		
		if(!reg.test(email)){
			
			e.preventDefault();
			
			const error = document.getElementById("emailerror");
			error.style.display = 'inline';
			error.style.margin = 0;
			error.style.color = 'red';
			error.innerHTML = 'Enter a valid email'
		}
		
		if(pass1.length<5 ){
			e.preventDefault();
			
			const error = document.getElementById("pass1error");
			error.style.display = 'inline';
			error.style.margin = 0;
			error.style.color = 'red';
			error.innerHTML = 'Minimum password length is 5'
		}
		
		if(pass1!=pass2){
			e.preventDefault();
			
			const error = document.getElementById("pass2error");
			error.style.display = 'inline';
			error.style.margin = 0;
			error.style.color = 'red';
			error.innerHTML = 'Enter matching password'
		}
		
		
	});

}
*/