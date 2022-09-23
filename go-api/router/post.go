package router

import (
	"personal-blog/controllers"
	"personal-blog/middleware"

	"github.com/gofiber/fiber/v2"
)

func PostRouter(app *fiber.App) {
	api := app.Group("/api/post", middleware.Authorization)

	api.Post("/add", controllers.AddPost)
}
