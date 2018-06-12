jQuery(function () {
    window.particlesJS && particlesJS("particles-js", {
        particles: {
            color: "#fff",
            shape: "circle",
            opacity: 1,
            size: 2,
            size_random: !0,
            nb: 100,
            line_linked: {
                enable_auto: !0,
                distance: 70,
                color: "#fff",
                opacity: .5,
                width: 1,
                condensed_mode: {
                    enable: !1,
                    rotateX: 600,
                    rotateY: 600
                }
            },
            anim: {
                enable: !0,
                speed: 2.5
            }
        },
        interactivity: {
            enable: !0,
            mouse: {
                distance: 250
            },
            detect_on: "canvas",
            mode: "grab",
            line_linked: {
                opacity: .35
            },
            events: {
                onclick: {
                    enable: !0,
                    mode: "push",
                    nb: 3
                }
            }
        },
        retina_detect: !0
    })
})