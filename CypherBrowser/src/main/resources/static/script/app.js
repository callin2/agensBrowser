;(function () {
    var local = {
        LoginHtmlTmpl   : '',
        AdminTmpl       : '',
        UserTmpl        : ''
    }

    $(init)

    function init() {
        initData()
        initEvent()
    }

    function initData() {
        local.LoginHtmlTmpl = $('#loginTmpl').text();
        local.UserTmpl = $('#userTmpl').text();
        local.AdminTmpl = $('#adminTmpl').text();
        console.log(local);
    }


    function initEvent() {
        $('.menu .login').click(onLoginClick)
        $('.menu .usermenu').click(onUsermenuClick)
        $('.menu .adminmenu').click(onAdminmenuClick)

        // inline style handler
        $('.content').on('submit','#loginForm', (evt)=>{
            console.log('login clicked')
            evt.preventDefault()

            var connectUrl = $('#url').val()
            var id = $('#username').val()
            var pwd = $('#password').val()


            AG_API.issueToken(connectUrl, id, pwd)
            .then((rslt)=>{console.log(rslt)})
            .catch(()=>{})
        })
    }


    function onLoginClick() {
        $('.content').html(local.LoginHtmlTmpl)
    }

    function onUsermenuClick() {
        $('.content').html(local.UserTmpl)
    }

    function onAdminmenuClick() {
        $('.content').html(local.AdminTmpl)
    }
})();
