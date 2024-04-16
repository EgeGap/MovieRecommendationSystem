function createFilmInputs() {
    var filmCount = document.getElementById("filmCount").value;
    var filmInputsDiv = document.getElementById("filmInputs");
    filmInputsDiv.innerHTML = ""; 

    for (var i = 0; i < filmCount; i++) {
      var input = document.createElement("input");
      input.type = "text";
      input.name = "filmNames"; 
      input.placeholder = "Please enter movie name";
      filmInputsDiv.appendChild(input);
      filmInputsDiv.appendChild(document.createElement("br"));
    }
  }

  function submitForm() {
    var filmCount = document.getElementById("filmCount").value;
    var filmInputs = document.getElementsByName("filmNames");
    var isValid = true;

    for (var i = 0; i < filmInputs.length; i++) {
      if (filmInputs[i].value.trim() === "") {
        isValid = false;
        break;
      }
    }

    if (filmCount < 1 || filmCount > 5 || !isValid) {
      alert("Please enter valid movie names and select the number of movies.");
      return false; 
    }

    var userEmail = document.getElementById("userEmail").value;
    if (userEmail.trim() === "") {
      alert("Please enter a valid email address.");
      return false; 
    }

    return true; 
  }