const models = document.querySelectorAll(".model-img");
const modelsOverlay = document.querySelectorAll(".model-img-overlay");

models.forEach((model, index) => {
    model.addEventListener("mouseover", () => {
        modelsOverlay[index].style.height = "30%";
    });
});

models.forEach((model, index) => {
    model.addEventListener("mouseleave", () => {
        modelsOverlay[index].style.height = 0;
    });
});
