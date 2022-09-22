package main

import (
	"fmt"
	"personal-blog/config"
	"personal-blog/router"

	"github.com/gofiber/fiber/v2"
)

func main() {
	app := fiber.New()

	config.Connect()

	router.AuthRouter(app)

	fmt.Println(app.Listen(":3000"))
}
