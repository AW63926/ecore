const teacherLink = document.querySelector('#teacherLink');
const mainDiv = document.querySelector('.main');
const mainTeachers = '<div id="mainTeachers">' +
'<h2>Teachers</h2>' +
'<div th:each= "teachers: ${teachers}">' +
    '<a th:href="@{/teacher(id=${teachers.id})}" th:text = "${teachers.name}"></a>' +
'</div>' +
'<form method="POST">' +
'<h3>Add A Teacher</h3>'

'<div>' +
    '<label for="title">Teacher Name:</label>' + '<input id="title" type="text" name="name" placeholder="Enter Teacher Name" required="required">' +
'</div>' +

'<div>' + 

    '<label for="specialty">Specialty</label>' + '<input id="specialty" type="text" name="teacherSpecialty" placeholder="Please Enter Specialty" required="required">' +
'</div>' +

'<div>' + 
    '<label for="school name">School Name:</label> <input id="school name" type="text" name="school" placeholder="School Name" required="required">' +
'</div>' +

'<div>' +
    '<button th:formaction="@{/add-teacher}">Add Teacher</button>' +
'</div>' +
'</form>' +
'</div>';

teacherLink.addEventListener('click', function(){
    postTeachers();
})

function postTeachers() {
    mainDiv.innerHTML = mainTeachers;
}