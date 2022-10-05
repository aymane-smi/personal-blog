package controllers

import (
	"fmt"
	"personal-blog/config"
	"personal-blog/models"

	"github.com/gofiber/fiber/v2"
)

var post models.Post
var tmp_post models.Post

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

	if errFile != nil || errForm != nil {
		return c.Status(fiber.StatusInternalServerError).JSON(fiber.Map{
			"message": "something went wrong",
		})
	}

	if form.Value["id"][0] == "" {
		return c.Status(fiber.StatusNotAcceptable).JSON(fiber.Map{
			"message": "inavlid id",
		})
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

	if form.File != nil {
		post.ImagePath = file.Filename
		c.SaveFile(file, fmt.Sprintf("./images/%s", file.Filename))
	}

	config.DB.Where("id = ?", form.Value["id"][0]).First(&tmp_post).Update()

}
