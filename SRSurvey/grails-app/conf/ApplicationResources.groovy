modules = {
    application {
        resource url:'js/application.js'
    }
    core {
        dependsOn 'jquery'
        resource 'js/jquery.fancybox.js'
        resource 'css/jquery.fancybox.css'
        resource 'css/survey.css'
    }
}