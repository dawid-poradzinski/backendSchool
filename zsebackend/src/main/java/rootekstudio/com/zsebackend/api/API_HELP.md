Auth: {

    ====User Creation====
    link POST: /auth/create
    required: admin rank
    JSON: {
        "username": String,
        "fullName": String,
        "email": String,
        (OPTIONAL DEFAULT 'USER') "rank": | USER,WORKER,ADMIN 
    }
    return: password

    ====User Login====
    link POST: /auth/login
    JSON: {
        "usernameOrEmail": String,
        "password": String,
    }
    return: "jwt": String

    ====User Password Reset====
    link PUT: /auth/me/password/reset
    required: jwt token
    JSON: {
        oldPassword: String,
        newPassword: String
    }
    return: boolean;

    ====User Password Reset By Admin====
    link PUT: /auth/admin/{id}/password/reset
    required: {
        ADMIN rank,
        id of existing user - Long
    }
    return: new password - String

    ====Rank Change By Admin====
    link PUT: /auth/admin/{id}/rank/change
    required: {
        ADMIN rank
        id of existing user - Long
    }
    JSON: {
        rank: String - /"ADMIN","WORKER","USER"/
    }
    return: boolean
}

Post: {

    JSON {
        "id": Long,
        "title": String.
        "creationDate": LocalDateTime,
        "description": String,
        "fullName": String
    }

    ====Add Post=====
    link PUT: /post/add
    required: {
        admin / worke rank,
        post doesn't exist
    }
    JSON: {
        "title": String,
        "description": String,
        "mainImage": String
    }

    ====Delete By Id====
    link DELETE: /post/delete/{id}
    required: {
        id: int - id of post
        admin / worker rank
    }
    return: boolean

    ====Change Post====
    link PUT: /post/change
    required: {
        admin / worker rank,
        post exist
    }
    JSON: {
        "id": int,
        "title": String,
        description: String,
        mainImage, String
    }
    return: Post

    ====Get Single Post====
    link POST: /post/get/{id}
    required: id: int - id of post
    return: Post

    ====Get All Posts====
    link POST: /post/get/all 
    return: List<Post>

    ====Get All Posts Limited====
    link POST : /post/get/all/limited{pageNumber}/{postsPerPage}
    required: {
        pageNumber: int - number of page
        postsPerPage: int - how much posts per page
    }
    return: List<Post>

    ====Get Posts Number====
    link POST: /post/count
    return: Int - number of posts

}

Element: {

    JSON: {
        "id": Long,
        "name": String,
        "settings": HashMap<String, Object>
        //////////
        "settings": {
            String: Object
            ...
        }
        /////////
    }

    ====Add Element====
    link PUT: /element/add
    required: {
        worker / admin rank,
        element doesn't exist
    }
    JSON: {
        "name": String,
        "settings": HashMap<String, Object>
    }
    return: Element

    ===Delete Element By Name====
    link DELETE: /element/delete/byName/{name}
    required:  {
        worker / admin rank,
        name - String: name of element
    }
    return: boolean

    ====Change Element====
    link PUT: /element/change
    required: {
        worker / admin rank,
        element exist
    }
    JSON: {
        "name": String,
        "settings": HashMap<String, Object>
    }
    
    ====Get Element By Name====
    link POST: /element/get/byName/{name}
    required: name - String: name of element
    return: Element

    ====Get All Elements====
    link POST: /element/get/all
    return: List<Element>
}

File: {
    ====Add file to server====
    link PUT: /page/upload
    required: {
        ADMIN / WORKER rank,
        file
    }
    JSON MULTIPART: {
        "file: : file`
    }
    return dir path

    ====Delete file from server====
    link DELETE: /page/delete/{name}
    required: {
        ADMIN / WORKER rank
        name - name of file with .txt - String
    }
    return: boolean
}

first user password: SzkolaKoNsTanTynO2Cy!Ki