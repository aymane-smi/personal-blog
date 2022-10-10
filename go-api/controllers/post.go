package controllers

import (
	"fmt"
	"personal-blog/config"
	"personal-blog/models"
	"strconv"

	"github.com/gofiber/fiber/v2"
)

var post models.Post
var tmp_post models.Post

//add a post

func AddPost(c *fiber.Ctx) error {

	//var body map[string]string

	form, errForm := c.MultipartForm()
	file, errFile := c.FormFile("file")

	if errForm != nil || errFile != nil {
		return c.Status(fiber.StatusInternalServerError).JSON(fiber.Map{
			"message": "something went wrong!",
		})
	}

	if form.Value["title"][0] == "" {
		return c.Status(fiber.StatusNotAcceptable).JSON(fiber.Map{
			"message": "title must be given",
		})
	} else {
		post.Title = form.Value["title"][0]
	}

	if form.Value["content"][0] == "" {
		return c.Status(fiber.StatusNotAcceptable).JSON(fiber.Map{
			"message": "content of the post must be given",
		})
	} else {
		post.Content = form.Value["content"][0]
	}

	if form.File != nil {
		post.ImagePath = file.Filename
		c.SaveFile(file, fmt.Sprintf("./images/%s", file.Filename))
	}

	config.DB.Create(&post)

	return c.Status(fiber.StatusOK).JSON(fiber.Map{
		"message": "post created",
		"post":    post,
	})
}

//edit post

func EditPost(c *fiber.Ctx) error {

	form, errForm := c.MultipartForm()
	file, errFile := c.FormFile("file")

	fmt.Println(form, file)

	if errFile != nil && errForm != nil {
		return c.Status(fiber.StatusInternalServerError).JSON(fiber.Map{
			"message": "something went wrong",
		})
	}
	if form.Value["id"][0] == "" {
		return c.Status(fiber.StatusNotAcceptable).JSON(fiber.Map{
			"message": "invalid id",
		})
	} else {
		config.DB.Where("id = ", form.Value["id"][0]).First(&post)
	}

	if form.Value["title"][0] == "" && form.Value["content"][0] == "" && form.File == nil {
		return c.Status(fiber.StatusNotAcceptable).JSON(fiber.Map{
			"message": "please provide atleast one field to edit",
		})
	}

	if form.Value["title"][0] != "" {
		post.Title = form.Value["title"][0]
	}

	if form.Value["content"][0] != "" {
		post.Content = form.Value["content"][0]
	}

	if file != nil {
		post.ImagePath = file.Filename
		c.SaveFile(file, fmt.Sprintf("./images/%s", file.Filename))
	}
	config.DB.Save(&post)

	return c.Status(fiber.StatusAccepted).JSON(fiber.Map{
		"message": "post updated",
		"post":    post,
	})

}

//delete a post

func DeletePost(c *fiber.Ctx) error {
	var body map[string]string

	fmt.Println(body)
	if err := c.BodyParser(&body); err != nil {
		return c.Status(fiber.StatusBadRequest).JSON(fiber.Map{
			"message": "something went wrong!",
		})
	}

	if _, err := strconv.Atoi(body["id"]); err != nil {
		return c.Status(fiber.StatusBadRequest).JSON(fiber.Map{
			"message": "the id must by a valid number",
		})
	}

	if err := config.DB.Where("id = ?", body["id"]).First(&post).Error; err != nil {
		return c.Status(fiber.StatusNotFound).JSON(fiber.Map{
			"message": "post with id(" + body["id"] + ") not found",
		})
	}

	config.DB.Delete(&post, body["id"])

	return c.Status(fiber.StatusOK).JSON(fiber.Map{
		"message": "post deleted!",
		"post":    post,
	})

}
