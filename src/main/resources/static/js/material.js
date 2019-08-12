const tagAddButton = document.querySelector('.addTag button');
const tagAddInput = document.querySelector('.addTag input');
const tagsList = document.querySelector('.tagsList');
const documentContainer = document.querySelector('.container');
const materialId = document.querySelector('.materialId');

  $(document).ready(function(){
  $(".navbar li").removeClass("active");
  $(".navbar li a").removeClass("active");
  $('#all-materials').addClass('active');
  });

const xhr = new XMLHttpRequest()
xhr.onreadystatechange = function(){
    if(xhr.readyState === 4 && xhr.status === 200){
        const res = xhr.responseText;
        tagsList.innerHTML = res;
    }
}

tagAddButton.addEventListener('click', function(){
    postTags(tagAddInput.value, materialId.value);
    console.log(tagAddInput.value);
    tagAddInput.value = "";

})

tagsList.addEventListener('click', function(event){
    if(event.target.classList.contains('x')){
        let tagId = event.target.previousElementSibling.previousElementSibling.value;
        console.log(tagId);
        removeTag(tagId);
    }
})

function postTags(tagName){
    const materialIdToadd = document.querySelector('.materialId');
    xhr.open('POST', '/materials/tags/' + tagName + '/' + materialIdToadd.value, true);
    xhr.send();
}

function removeTag(tagId){
    const materialIdToRemove = document.querySelector('.materialId');
    xhr.open('POST', '/materials/tags/remove/' + tagId + '/' + materialIdToRemove.value, true);
    xhr.send();
}

window.onload = function() {
    window.setTimeout(fadeout, 8000); //8 seconds
  }
  
  function fadeout() {
    document.getElementById('fadeout').style.opacity = '0';
  }
