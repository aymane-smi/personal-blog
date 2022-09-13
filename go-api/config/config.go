package config

import (
	"fmt"
	"log"
	"os"
	"personal-blog/models"

	"github.com/joho/godotenv"
	"gorm.io/driver/mysql"
	"gorm.io/gorm"
)

var DB *gorm.DB

func Connect() {
	err := godotenv.Load()

	if err != nil {
		log.Fatal(err)
	}

	db, err := gorm.Open(mysql.Open(os.Getenv("DSN")), &gorm.Config{})

	if err != nil {
		panic(err)
	} else {
		fmt.Println(db)
	}

	DB = db

	DB.AutoMigrate(&models.User{}, &models.Post{})
}
