function cover5() {
    var cover = document.getElementById('warning');
    if (vis) {
        vis = 0; 
        cover.style.display='block';
    }
    else{
        vis = 1;
        cover.style.display='none';
    }
}
