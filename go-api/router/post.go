package router

import (
	"personal-blog/controllers"

	"github.com/gofiber/fiber/v2"
)

func PostRouter(app *fiber.App) {
	api := app.Group("/api/post")

	api.Post("/add", controllers.AddPost)
	api.Put("/edit", controllers.EditPost)
	api.Delete("/delete", controllers.DeletePost)
}
