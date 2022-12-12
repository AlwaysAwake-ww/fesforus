const category = document.getElementsByClassName("category");


document.getElementById("btnWrite").addEventListener(()=>{
    category_insert();
    $("#festivalWrite").submit();

});


function category_insert() {

    category_value.value='';

    Array.prototype.forEach.call(category, (e) => {

        if (e.checked) {
            category_value.value += e.value + ",";
        }
    });
};