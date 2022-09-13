
## Spring API docs

#### always try to add `Authorization` that contain token to the `Header` before any request

#### login

```http
  POST /api/auth/login
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
|`content`|`string`|body of the post|


#### see image

```http
  POST /api/post/images/{filename}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `filname`      | `string` |  name of the image|


#### edit user

```http
  POST /api/post/add
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `username` | `string` | **Required**. username(default:`aymane`) |
|`newPassword`|`string`|change the old password with the new|
|`Fullname`|`string`|change the fullname with this new one|
|`newUsername`|`string`|change the old username with the new|



