package router

import (
	"personal-blog/controllers"
	"personal-blog/middleware"

	"github.com/gofiber/fiber/v2"
)

func AuthRouter(app *fiber.App) {
	//api := app.Group("/api/auth", middleware.Authorization)
	app.Post("/api/auth/login", controllers.Login)
	app.Post("/api/auth/signup", controllers.SignUp)
	app.Put("/api/auth/edit", middleware.Authorization, controllers.EditUser)
}
