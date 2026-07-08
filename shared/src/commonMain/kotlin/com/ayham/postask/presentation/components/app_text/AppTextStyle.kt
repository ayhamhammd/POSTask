package com.ayham.postask.presentation.components.app_text

enum class TextCategory {
    Headline,
    Title,
    Label,
    Body,
}

enum class TextSize {
    Large,
    Medium,
    Small,
}

data class TextStyleConfig(
    val category: TextCategory,
    val size: TextSize,
    val emphasized: Boolean,
)

class TextCategoryWithSizes(private val category: TextCategory) {
    val Large = TextStyleConfig(category, TextSize.Large, emphasized = false)
    val LargeEmphasized = TextStyleConfig(category, TextSize.Large, emphasized = true)
    val Medium = TextStyleConfig(category, TextSize.Medium, emphasized = false)
    val MediumEmphasized = TextStyleConfig(category, TextSize.Medium, emphasized = true)
    val Small = TextStyleConfig(category, TextSize.Small, emphasized = false)
    val SmallEmphasized = TextStyleConfig(category, TextSize.Small, emphasized = true)
}

object AppTextStyle {
    val Headline = TextCategoryWithSizes(TextCategory.Headline)
    val Title = TextCategoryWithSizes(TextCategory.Title)
    val Label = TextCategoryWithSizes(TextCategory.Label)
    val Body = TextCategoryWithSizes(TextCategory.Body)
}
