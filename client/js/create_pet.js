var api = "http://localhost:9966/petclinic/api/";

function getPetTypes() {
    var petType = httpGet(api + 'pettypes');
    console.log(petType);
    return petType;
}

function getOwners() {
    var ownerType = httpGet(api + 'owners');
    console.log(ownerType);
    return ownerType;
}

function createPet(event) {
    if (event.preventDefault) {
        event.preventDefault();
    }
    var namePet = document.getElementById('name').value;
    var date = document.getElementById('birthDate').value;
    var petType = document.getElementById('type');
    var petTypeEl = petType.options[petType.selectedIndex].value;
    var owner = document.getElementById('owner');
    var ownerEl = owner.options[owner.selectedIndex].value;
    var body = {
        name: namePet,
        birthDate: date,
        typeId: petTypeEl,
        ownerId: ownerEl
    };
    console.log(body);
    httpPost(api + 'pets', body);
}

function httpGet(theUrl) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", theUrl, false); // false for synchronous request
    xmlHttp.setRequestHeader("Content-Type", "application/json");
    xmlHttp.send(null);
    return JSON.parse(xmlHttp.responseText);
}

function httpPost(theUrl, body) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", theUrl, false); // false for synchronous request
    xmlHttp.setRequestHeader("Content-Type", "application/json");
    xmlHttp.send(JSON.stringify(body));
    return JSON.parse(xmlHttp.responseText);
}
var petType = getPetTypes();
var ownerTypes = getOwners();
var select = document.getElementById('type');
var select2 = document.getElementById('owner');
for (var i = 0; i < petType.length; i++) {
    var type = petType[i];
    var name = type.name;
    var option = '<option value ="' + type.id + '">' + name.charAt(0).toUpperCase() + name.slice(1) + '</option>';
    select.innerHTML += option;
}

for (var i = 0; i < ownerTypes.length; i++) {
    var owner = ownerTypes[i];
    var option = '<option value ="' + owner.id + '">' + owner.firstName + ' ' +  owner.lastName +  '</option>';
    select2.innerHTML += option;
}
var submit = document.getElementById('submit');
submit.addEventListener("click", createPet, false);


