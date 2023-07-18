function onClickSelect(e) {
        const isActive = e.currentTarget.className.indexOf("active") !== -1;
        if (isActive) {
          e.currentTarget.className = "select";
        } else {
          e.currentTarget.className = "select active";
        }
      }

      document.querySelector("#theme .select").addEventListener("click", onClickSelect);

      function onClickOption(e) {
        const selectedValue = e.currentTarget.innerHTML;
        document.querySelector("#theme .text").innerHTML = selectedValue;
      }

      var optionList = document.querySelectorAll("#theme .option");
      for (var i = 0; i < optionList.length; i++) {
        var option = optionList[i];
        option.addEventListener("click", onClickOption);
      }
