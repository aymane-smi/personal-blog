
## GO API docs

#### always try to add `Authorization` that contain token to the `Header` before any request

#### login

```http
  POST /api/auth/login
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `username` | `string` | **Required**. username(default:`aymane`) |
|`password`|`string`|**Required**. password(default: `aymane`)|

#### Signup

```http
  POST /api/auth/ignup
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `username` | `string` | **Required**. username(default:`aymane`) |
|`password`|`string`|**Required**. password(default: `aymane`)|

#### add Post

```http
  POST /api/post/add
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `file`      | `file` |  image that belong to the post|
|`content`|`string`|body of the post **Required**|
|`title`|`string`|titleofthe post **Required**|

#### edit Post

```http
  PUT /api/post/edit
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `file`      | `file` |  image that belong to the post|
|`content`|`string`|body of the post |
|`title`|`string`|titleofthe post|

#### delete Post

```http
  DELETE /api/post/delete
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `int` |  **Required** the id of the post|



#### see image

```http
  GET /api/post/images/{filename}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `filname`      | `string` |  name of the image|


