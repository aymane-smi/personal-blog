package controllers

import (
	"fmt"
	"os"
	"personal-blog/config"
	"personal-blog/models"
	"strconv"
	"time"

	"github.com/dgrijalva/jwt-go"
	"github.com/gofiber/fiber/v2"
	"golang.org/x/crypto/bcrypt"
)

var user models.User

func SignUp(c *fiber.Ctx) error {
	var body map[string]string
	if err := c.BodyParser(&body); err != nil {
		return c.Status(fiber.StatusBadRequest).JSON(fiber.Map{
			"message": "bad request!",
		})
	}

	if body["username"] == "" {
		return c.Status(fiber.StatusBadRequest).JSON(fiber.Map{
			"message": "username is missing",
		})
	} else {
		user.Username = body["username"]
	}

	if body["password"] == "" {
		return c.Status(fiber.StatusBadRequest).JSON(fiber.Map{
			"message": "password is required",
		})
	} else {
		password, _ := bcrypt.GenerateFromPassword([]byte(body["password"]), 12)
		user.Password = string(password)
	}

	if body["fullname"] == "" {
		return c.Status(fiber.StatusBadRequest).JSON(fiber.Map{
			"message": "the user fullname must be given",
		})
	} else {
		user.Fullname = body["fullname"]
	}

	config.DB.Create(&user)

	return c.Status(fiber.StatusAccepted).JSON(fiber.Map{
		"message": "signed in!",
		"user":    user,
	})
}

func Login(c *fiber.Ctx) error {

	var body map[string]string

	if err := c.BodyParser(&body); err != nil {
		return c.Status(fiber.StatusBadRequest).JSON(fiber.Map{
			"message": err,
		})
	}

	if body["username"] == "" {
		return c.Status(fiber.StatusBadGateway).JSON(fiber.Map{
			"message": "invalid username",
		})
	}

	if body["password"] == "" {
		return c.Status(fiber.StatusBadGateway).JSON(fiber.Map{
			"message": "invalid password",
		})
	}

	config.DB.Where("username = ?", body["username"]).First(&user)

	if user.Username == "" {
		return c.Status(fiber.StatusNotFound).JSON(fiber.Map{
			"message": "invalid password",
		})
	}

	if err := bcrypt.CompareHashAndPassword([]byte(user.Password), []byte(body["password"])); err != nil {
		return c.Status(fiber.StatusNotAcceptable).JSON(fiber.Map{
			"message": "invalide password, please try again!",
		})
	}

	claims := jwt.NewWithClaims(jwt.SigningMethodHS256, &jwt.StandardClaims{
		Issuer:    strconv.Itoa(int(user.ID)),
		ExpiresAt: time.Now().Add(time.Hour * 24).Unix(),
	})

	token, token_err := claims.SignedString([]byte(os.Getenv("SECRET_KEY")))

	if token_err != nil {
		return c.Status(fiber.StatusInternalServerError).JSON(fiber.Map{
			"message": "something went wrong!",
		})
	}

	return c.Status(fiber.StatusAccepted).JSON(fiber.Map{
		"message": "logged in!",
		"user":    user,
		"token":   token,
	})
}

//edit user

func EditUser(c *fiber.Ctx) error {
	var body map[string]string

	if err := c.BodyParser(&body); err != nil {
		c.Status(fiber.StatusBadRequest).JSON(fiber.Map{
			"message": "something went wrong!",
		})
	}

	if body["username"] == "" && body["password"] == "" && body["fullname"] == "" {
		return c.Status(fiber.StatusOK).JSON(fiber.Map{
			"message": "no data provided!",
		})
	}

	id, _ := strconv.Atoi(body["id"])
	config.DB.Where("id = ?", id).First(&user)
	fmt.Println(user)

	if body["username"] != "" {
		user.Username = body["username"]
	}

	if body["password"] != "" {
		password, _ := bcrypt.GenerateFromPassword([]byte(body["password"]), 12)
		user.Password = string(password)
	}

	if body["fullname"] != "" {
		user.Fullname = body["fullname"]
	}

	config.DB.Save(&user)

	return c.Status(fiber.StatusOK).JSON(fiber.Map{
		"message": "user updated!",
		"user":    user,
	})
}
