var AG_API = {
    issueToken : function(id,pwd,url) {
        return $.ajax({
            method: 'POST',
            data: {username:id, password:pwd},
            url : '/login',
            dataType: 'json',
        })
    }
}