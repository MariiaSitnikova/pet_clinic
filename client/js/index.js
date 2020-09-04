var api = "http://localhost:9966/petclinic/api/";

var pets = httpGet(api + "pets");

var list = document.getElementById('pets');
for (var i = 0; i < pets.length; i++) {
    var pet = pets[i];
    printPet(pet);
}

function printPet(pet) {
    var fullName = pet.owner.lastName + ' ' + pet.owner.firstName;
    var type = pet.type.name;
    var petName = pet.name;
    var li = '<li>' + fullName + ' - ' + type + ': ' + petName + '</li>';
    list.innerHTML += li;
}

function httpGet(theUrl) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", theUrl, false); // false for synchronous request
    xmlHttp.send(null);
    return JSON.parse(xmlHttp.responseText);
}

