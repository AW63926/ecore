const endpoint = 'http://localhost:8080/needs'

const needs = [];

fetch(endpoint)
.then(blob => blob.json())
.then(data => needs.push(...data))
function findMatches(wordToMatch, needs){
    return needs.filter(allNames => {
        const regex = new RegExp(wordToMatch, 'gi');
        return allNames.name.match(regex)
    });
}

function displayMatches(){
const matchArray = findMatches(this.value, needs);
const html = matchArray.map(allNames =>{
    return `
    <li>
    <span class = "name">
    <a href = "/need/community?id=${allNames.id}" target="main">${allNames.name}</a>
    </span>
    </li>
    `;
}).join('');
suggestions.innerHTML = html;
}

const searchInput = document.querySelector('.search');
const suggestions = document.querySelector('.suggestions');

searchInput.addEventListener('change', displayMatches);
searchInput.addEventListener('keyup', displayMatches);

