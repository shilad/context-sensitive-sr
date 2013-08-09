modules = {
    application {
        resource url:'js/application.js'
    }
    core {
        dependsOn 'jquery'
        dependsOn 'jquery-ui'
        resource 'js/jquery.fancybox.js'
        resource 'css/jquery.fancybox.css'
        resource 'css/style.css'
    }
    old {
        dependsOn 'jquery'
        resource 'js/jquery.fancybox.js'
        resource 'css/jquery.fancybox.css'
        resource 'css/survey.css'
        resource 'css/email.css'
    }
}