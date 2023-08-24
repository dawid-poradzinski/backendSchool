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

}

Post: {

    JSON {
        "id": Long,
        "title": String.
        "creationDate": LocalDateTime,
        "description": String,
        "username": String
    }

    ====Add Post=====
    link PUT: /post/add
    required: admin / worke rank
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
    required: admin / worker rank
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

    ====Add Post====
    link PUT: /post/add
    required: admin / worker rank
    JSON: {
        "title": String,
        "description": String,
        "mainImage": String
    }
    return: Post

    ====Get All Posts====
    link POST: /post/get/all 
    return: List<Post>

    ====Get All Posts Limited====
    link POST : /post/get/all/limited{startFrom}/{limit}
    required: {
        startFrom: int - number from newest post
        limit: int - how much show
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
    required: worker / admin rank
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
    required: worker / admin rank
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