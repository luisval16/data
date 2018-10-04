jQuery(function($){
    /* $(document).on("scroll", function() {
        if($(document).scrollTop()>100) {
            $("header").removeClass("large").addClass("small");
        } else {
            $("header").removeClass("small").addClass("large");
        }
    }); */
    window.sr = ScrollReveal();
    sr.reveal('.revelar');

    sr.reveal('.revelar-izquierda', { origin: 'right', duration: 500, viewFactor  : 0.2, distance : '500px', opacity : 0, scale:0.2 });

    sr.reveal('.revelar-derecha', { origin: 'left', duration: 500, viewFactor  : 0.2, distance : '500px', opacity : 0, scale:0.2 });


    sr.reveal('.revelar-delay-1', { delay: 200 * 1 });
    sr.reveal('.revelar-delay-2', { delay: 200 * 2 });
    sr.reveal('.revelar-delay-3', { delay: 200 * 3 });
    sr.reveal('.revelar-delay-4', { delay: 200 * 4 });

    $("#footer-container").load("footer.html");
});