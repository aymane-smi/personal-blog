package models

type User struct {
	ID       uint   `gorm:"PrimaryKey" json:"id"`
	Fullname string `json:"fullname"`
	Username string `json:"username"`
	Password string `json:"password"`
}
