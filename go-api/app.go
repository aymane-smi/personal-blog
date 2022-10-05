package main

import (
	"fmt"
	"personal-blog/config"
	"personal-blog/router"

	"github.com/gofiber/fiber/v2"
)

func main() {
	app := fiber.New()

	app.Static("/api/post/images", "./images")

	config.Connect()

	router.AuthRouter(app)

	router.PostRouter(app)

	fmt.Println(app.Listen(":3000"))
}
