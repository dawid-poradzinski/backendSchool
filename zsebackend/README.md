
# Backend SpringBoot for School Project

Scrapped first project for school backend in springboot


## Run Locally

Clone the project

```bash
  git clone https://github.com/dawid-poradzinski/backendSchool
```

Go to the project directory

```bash
  cd backendschool/zsebackend
```

Change SQL connection (default mysql)
src/main/resources/application.properties

```bash
  spring.datasource.url=jdbc:mysql://localhost:3306/zsebackend
  spring.datasource.username=root
  spring.datasource.password=
```

Start the server from backendschool/zsebackend

```bash
  ./mvnw spring-boot:run
```


## API Reference

>**FULL API_HELP**
>in src/main/api/API_HELP.md

#### Login Auth

```http
  POST /auth/login
```
>**Warning**
>default admin:
>username: **bot_admin**
>password: **SzkolaKoNsTanTynO2Cy!Ki**

| Parameter         | Type     | Description                          |
| :---------------- | :------- | :----------------------------------- |
| `usernameOrEmail` | `string` | **Required**. Your username or email |
| `password`        | `string` | **Required**. Your password          |

**return**

| Parameter | Type | Description |
| :------ | :----- | :---------- |
| `jwt` | `string` | **jwt** token |



#### Create User Auth

```http
  POST /auth/create
```

**Required** jwt bearer token with admin rank
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `username`      | `string` | **Required**. Username of new user |
| `fullName` | `string` | **Required**. Full name of new user |
| `email` | `email` | **Required**. Email of new user |

**return**

| Parameter | Type | Description |
| :------ | :----- | :---------- |
| `user` | `user` | User informations |
| `temporaryPassword` |`string` | Password for first login |

#### Post

| Parameter | Type | Description |
| :------ | :----- | :---------- |
| `~id` | `int` | Post ID |
| `title` |`string` | Post title |
| `creationDate` | `date` | Post creation date|
|`description` | `string` | Post description |
| `mainImage` | `string` | Link to post main image|
|`fullName` | `string` | FullName of creator |
| `images` | `array[string]` | array of links to images|


#### Add Post

```http
  PUT /post/add
```
**Required** jwt bearer token with admin or worker rank

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `title`      | `string` | **Required**. Title for new post |
| `description` | `string` | **Required**. Description for new post |
| `mainImage` | `email` | **Required**. Link to main image for new post |
| `images` | `array[string]`| Links to additionals images for new post |

**return** post entity

#### Get All Posts

```http
  POST /post/get/all
```
**return** array[post]

#### Get Single Post

```http
  POST /post/get/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `int` | **Required**. Post ID|

**return** post entity

#### Delete Single Post

```http
  DELETE /post/delete/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `int` | **Required**. Post ID|

**return** boolean