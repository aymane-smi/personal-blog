package models

type Post struct {
	ID        uint   `gorm:"PrimaryKey" json:"id"`
	Title     string `grom:"title" json:"title"`
	Content   string `json:"content"`
	ImagePath string `json:"file"`
}
