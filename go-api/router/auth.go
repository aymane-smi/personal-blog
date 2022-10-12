package router

import (
	"personal-blog/controllers"

	"github.com/gofiber/fiber/v2"
)

func AuthRouter(app *fiber.App) {
	app.Post("/api/auth/login", controllers.Login)
	app.Post("/api/auth/signup", controllers.SignUp)
	app.Put("/api/auth/edit", controllers.EditUser)
}
