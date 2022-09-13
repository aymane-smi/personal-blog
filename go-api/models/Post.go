package models

type Post struct {
	ID        uint   `gorm:"PrimaryKey" json:"id"`
	Content   string `json:"content"`
	ImagePath string `json:"file"`
}
