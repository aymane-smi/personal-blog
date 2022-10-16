package middleware

import (
	"os"
	"strings"

	"github.com/dgrijalva/jwt-go"
	"github.com/gofiber/fiber/v2"
	"github.com/joho/godotenv"
)

func Authorization(c *fiber.Ctx) error {
	godotenv.Load()
	token := strings.Split(string(c.Context().Request.Header.Peek("Authorization")), " ")

	if strings.Contains(c.Context().Request.Header.String(), "Authorization") == false {
		return c.Status(fiber.StatusUnauthorized).JSON(fiber.Map{
			"message": "token not found",
		})
	}

	_, err := jwt.ParseWithClaims(token[1], &jwt.StandardClaims{}, func(t *jwt.Token) (interface{}, error) {
		return []byte(os.Getenv("SECRET_KEY")), nil
	})

	if err != nil {
		return c.Status(fiber.StatusUnauthorized).JSON(fiber.Map{
			"message": "inavlid token",
		})
	}
	return c.Next()
}
