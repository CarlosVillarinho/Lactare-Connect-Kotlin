package br.com.carlosvillarinho.lactareconnect.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import br.com.carlosvillarinho.lactareconnect.R

/**
 * Tipografia do app. A fonte Google Sans Flex e baixada em tempo de execucao
 * pelo Downloadable Fonts, usando o provedor do Google Play Services.
 * Se o download falhar (aparelho sem Play Services, por exemplo), o Compose
 * cai automaticamente para a fonte padrao do sistema.
 */
private val provedorGoogleFonts = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

private val googleSansFlex = GoogleFont(name = "Google Sans Flex")

val FonteLactare = FontFamily(
    Font(googleFont = googleSansFlex, fontProvider = provedorGoogleFonts, weight = FontWeight.Normal),
    Font(googleFont = googleSansFlex, fontProvider = provedorGoogleFonts, weight = FontWeight.Medium),
    Font(googleFont = googleSansFlex, fontProvider = provedorGoogleFonts, weight = FontWeight.SemiBold),
    Font(googleFont = googleSansFlex, fontProvider = provedorGoogleFonts, weight = FontWeight.Bold)
)

val LactareTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = FonteLactare, fontWeight = FontWeight.Bold,
        fontSize = 40.sp, lineHeight = 46.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = FonteLactare, fontWeight = FontWeight.Bold,
        fontSize = 30.sp, lineHeight = 36.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = FonteLactare, fontWeight = FontWeight.Bold,
        fontSize = 24.sp, lineHeight = 30.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FonteLactare, fontWeight = FontWeight.Bold,
        fontSize = 20.sp, lineHeight = 26.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FonteLactare, fontWeight = FontWeight.Bold,
        fontSize = 17.sp, lineHeight = 22.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FonteLactare, fontWeight = FontWeight.Normal,
        fontSize = 16.sp, lineHeight = 23.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FonteLactare, fontWeight = FontWeight.Normal,
        fontSize = 14.sp, lineHeight = 20.sp
    ),
    labelLarge = TextStyle(
        fontFamily = FonteLactare, fontWeight = FontWeight.Bold,
        fontSize = 15.sp, lineHeight = 20.sp
    ),
    labelMedium = TextStyle(
        fontFamily = FonteLactare, fontWeight = FontWeight.Medium,
        fontSize = 13.sp, lineHeight = 18.sp
    )
)