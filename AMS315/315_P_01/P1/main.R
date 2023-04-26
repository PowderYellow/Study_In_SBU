# Created on :4/9/23

wdir <- getwd()

setwd(wdir)

PartA_IV <- read.csv('321782_IV.csv', header = TRUE)
PartA_DV <- read.csv('321782_DV.csv', header = TRUE)


# merge them to be one file
PartA <- merge(PartA_IV, PartA_DV, by = 'ID')

str(PartA)

View(PartA)


# assess how many missing values are in the data set

any(is.na(PartA[,2]) == TRUE)
any(is.nan(PartA[,2]) == TRUE)
any(is.na(PartA[,3]) == TRUE)
any(is.nan(PartA[,3]) == TRUE)


# Imputation of the missing values with package  ##mice

PartA_incomplete <- PartA
#install.packages('mice')
library(mice)

md.pattern(PartA_incomplete)
#photo_1
#    ID DV IV
# 509  1  1  1   0
# 53   1  1  0   1
# 46   1  0  1   1
# 28   1  0  0   2
#     0 74 81 155

PartA_imp <- PartA[!is.na(PartA$IV)==TRUE|!is.na(PartA$DV)==TRUE,]

imp <- mice(PartA_imp, method = "norm.boot", printFlag = FALSE)

PartA_complete <- complete(imp)

md.pattern(PartA_complete)
#     ID IV DV
# 608  1  1  1 0
#      0  0  0 0


# More about Simple Regression
# We first fit a regression model to the data set and save it to an object

M <- lm(DV ~ IV, data=PartA_complete)
summary(M)


#
#Call:
#lm(formula = DV ~ IV, data = PartA_complete)
#
# Residuals:
#      Min       1Q   Median       3Q      Max
# -22.1200  -5.7146   0.1686   5.1042  24.5614
#
# Coefficients:
#             Estimate Std. Error t value Pr(>|t|)
# (Intercept)  39.6600     0.8844   44.84   <2e-16 ***
# IV            4.9604     0.1653   30.01   <2e-16 ***
# ---
# Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1
#
# Residual standard error: 7.943 on 606 degrees of freedom
# Multiple R-squared:  0.5977,	Adjusted R-squared:  0.5971
# F-statistic: 900.4 on 1 and 606 DF,  p-value: < 2.2e-16
#


#install.packages('knitr')
library(knitr)
kable(anova(M), caption='ANOVA Table')

#
# Table: ANOVA Table
#
# |          |  Df|   Sum Sq|     Mean Sq|  F value| Pr(>F)|
# |:---------|---:|--------:|-----------:|--------:|------:|
# |IV        |   1| 59022.62| 59022.61930| 924.1307|      0|
# |Residuals | 606| 38704.17|    63.86826|       NA|     NA|



plot(PartA_complete$DV ~ PartA_complete$IV, main='Scatter : DV ~ IV', xlab='IV', ylab='DV', pch=20)
abline(M, col='red', lty=3, lwd=2)
legend('topleft', legend='Estimated Regression Line', lty=3, lwd=2, col='red')
#photo_2


# Then we can calculate the confidence interval of the slope.
# 95% CI of slope:
confint(M, level = 0.95)

#
#                 2.5 %    97.5 %
# (Intercept) 37.239643 40.769867
# IV           4.797456  5.460121

# 99% CI of slope:
confint(M, level = 0.99)
#                0.5 %    99.5 %
# (Intercept) 36.68232 41.327186
# IV           4.69284  5.564737




# P2:Binning data & Lack of Fit test

data <- read.csv('321782_PartB.csv', header = TRUE)
data_trans <- data.frame(xtrans=data$x, ytrans=data$y^(7/9))

groups <- cut(data_trans$xtrans,breaks=c(-Inf,seq(min(data_trans$xtrans)+0.3, max(data_trans$xtrans)-0.3,by=0.3),Inf))

table(groups)
# ----------------------------------------------------------------------------------
# -2/3
# data_trans <- data.frame(xtrans=data$x, ytrans=data$y^(-2/3))
# groups:
# (-Inf,1.31] (1.31,1.61] (1.61,1.91] (1.91,2.21] (2.21,2.51] (2.51,2.81]
#          32          29          23          24          42          40
# (2.81,3.11] (3.11,3.41] (3.41,3.71] (3.71,4.01] (4.01,4.31] (4.31,4.61]
#          46          30          26          38          21          27
# (4.61, Inf]
#          46
# ----------------------------------------------------------------------------------

#
#data_trans <- data.frame(xtrans=data$x, ytrans=data$y)
#(-Inf,1.31] (1.31,1.61] (1.61,1.91] (1.91,2.21] (2.21,2.51] (2.51,2.81] (2.81,3.11] (3.11,3.41] (3.41,3.71]
#         32          29          23          24          42          40          46          30          26
#(3.71,4.01] (4.01,4.31] (4.31,4.61] (4.61, Inf]
#         38          21          27          46

# ----------------------------------------------------------------------------------

# groups- 1/3
# (-Inf,1.31] (1.31,1.61] (1.61,1.91] (1.91,2.21] (2.21,2.51] (2.51,2.81]
#          32          29          23          24          42          40
# (2.81,3.11] (3.11,3.41] (3.41,3.71] (3.71,4.01] (4.01,4.31] (4.31,4.61]
#          46          30          26          38          21          27
# (4.61, Inf]
#          46

# ----------------------------------------------------------------------------------

# (-Inf,1.31] (1.31,1.61] (1.61,1.91] (1.91,2.21] (2.21,2.51] (2.51,2.81]
#          32          29          23          24          42          40
# (2.81,3.11] (3.11,3.41] (3.41,3.71] (3.71,4.01] (4.01,4.31] (4.31,4.61]
#          46          30          26          38          21          27
# (4.61, Inf]
#          46


x <- ave(data_trans$xtrans, groups)
data_bin <- data.frame(x=x, y=data_trans$ytrans)



install.packages("olsrr")
# apply the Lack of Fit test
library("olsrr")
fit_b <- lm(y ~ x, data = data_bin)
ols_pure_error_anova(fit_b)

# 2
#
#                           Analysis of Variance Table
# -------------------------------------------------------------------------------
#                 DF        Sum Sq         Mean Sq       F Value        Pr(>F)
# -------------------------------------------------------------------------------
# x                 1    968463926.64    968463926.64    2842.888    1.36031e-189
# Residual        422    156612817.46       371120.42
#  Lack of fit     11     16600749.78      1509159.07    4.430078    2.584372e-06
#  Pure Error     411    140012067.68       340661.97
# -------------------------------------------------------------------------------
# 8/7
# Lack of Fit F Test
# --------------
# Response :   y
# Predictor:   x
#
#                         Analysis of Variance Table
# ---------------------------------------------------------------------------
#                 DF      Sum Sq       Mean Sq      F Value        Pr(>F)
# ---------------------------------------------------------------------------
# x                 1    308462.34    308462.34     2862.941    3.735338e-190
# Residual        422     45280.58       107.30
#  Lack of fit     11     998.1447     90.74043    0.8421921         0.597805
#  Pure Error     411     44282.44     107.7432
# ---------------------------------------------------------------------------

# ------------------------------------------------------------------------------

#        1
#                        Analysis of Variance Table
# -------------------------------------------------------------------------
#                 DF      Sum Sq     Mean Sq      F Value        Pr(>F)
# -------------------------------------------------------------------------
# x                 1    74860.51    74860.51     2832.323    2.696311e-189
# Residual        422    11064.19    26.21845
#  Lack of fit     11    201.1332    18.28483    0.6918006        0.7466733
#  Pure Error     411    10863.05    26.43078
# -------------------------------------------------------------------------
#8/9
# Lack of Fit F Test
# --------------
# Response :   y
# Predictor:   x
#
#                        Analysis of Variance Table
# -------------------------------------------------------------------------
#                 DF      Sum Sq     Mean Sq      F Value        Pr(>F)
# -------------------------------------------------------------------------
# x                 1    24235.39    24235.39     2801.457    2.015409e-188
# Residual        422    3619.747    8.577599
#  Lack of fit     11    64.18757    5.835234    0.6745159        0.7629549
#  Pure Error     411    3555.559    8.650995
# -------------------------------------------------------------------------
# 6/8
# Lack of Fit F Test
# --------------
# Response :   y
# Predictor:   x
#
#                        Analysis of Variance Table
# -------------------------------------------------------------------------
#                 DF      Sum Sq     Mean Sq      F Value        Pr(>F)
# -------------------------------------------------------------------------
# x                 1     7611.83     7611.83     2764.501    2.298206e-187
# Residual        422    1154.201    2.735073
#  Lack of fit     11    22.54553    2.049594    0.7443813        0.6956382
#  Pure Error     411    1131.655     2.75342
# -------------------------------------------------------------------------





# -------------------------------------------------------------------------
#
#       - 1/3
#
#   Lack of Fit F Test
# --------------
# Response :   y
# Predictor:   x
#
#                           Analysis of Variance Table
# -------------------------------------------------------------------------------
#                 DF       Sum Sq         Mean Sq       F Value        Pr(>F)
# -------------------------------------------------------------------------------
# x                 1       0.202265        0.202265    2121.144    1.086096e-166
# Residual        422     0.04478956    0.0001061364
#  Lack of fit     11    0.005598015    0.0005089105    5.336921     6.364897e-08
#  Pure Error     411     0.03919155    9.535656e-05
# -------------------------------------------------------------------------------
# Lack of Fit F Test
#
#   -2/3
# Response :   y
# Predictor:   x
#
#                           Analysis of Variance Table
# ------------------------------------------------------------------------------
#                 DF       Sum Sq        Mean Sq       F Value        Pr(>F)
# ------------------------------------------------------------------------------
# x                 1     0.0585325       0.0585325    1871.634    3.186282e-157
# Residual        422    0.01540562     3.65062e-05
#  Lack of fit     11    0.00255222      0.00023202    7.419066      1.21767e-11
#  Pure Error     411     0.0128534    3.127347e-05
#
# -------------------------------------------------------------------------------
#
# -1
# Lack of Fit F Test
# --------------
# Response :   y
# Predictor:   x
#
#                            Analysis of Variance Table
# --------------------------------------------------------------------------------
#                 DF        Sum Sq         Mean Sq       F Value        Pr(>F)
# --------------------------------------------------------------------------------
# x                 1     0.009649794     0.009649794    1620.704    1.338379e-146
# Residual        422     0.003063265    7.258922e-06
#  Lack of fit     11    0.0006161404    5.601276e-05    9.407467     3.774326e-15
#  Pure Error     411     0.002447125    5.954075e-06
# --------------------------------------------------------------------------------




fit_b_final <- lm(ytrans ~ xtrans, data = data_trans)

summary(fit_b_final)


# ----------------------------------------------------------------------------------
#2
# Call:
# lm(formula = ytrans ~ xtrans, data = data_trans)
#
# Residuals:
#      Min       1Q   Median       3Q      Max
# -1748.10  -370.39    -1.44   372.88  1939.45
#
# Coefficients:
#             Estimate Std. Error t value Pr(>|t|)
# (Intercept)  -643.29      83.03  -7.748 7.04e-14 ***
# xtrans       1348.58      25.88  52.114  < 2e-16 ***
# ---
# Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1
#
# Residual standard error: 598.8 on 422 degrees of freedom
# Multiple R-squared:  0.8655,	Adjusted R-squared:  0.8652
# F-statistic:  2716 on 1 and 422 DF,  p-value: < 2.2e-16


# ----------------------------------------------------------------------------------
# 1
#
# Call:
# lm(formula = ytrans ~ xtrans, data = data_trans)
#
# Residuals:
#      Min       1Q   Median       3Q      Max
# -15.6108  -3.1087  -0.0178   3.0796  14.9373
#
# Coefficients:
#             Estimate Std. Error t value Pr(>|t|)
# (Intercept)  20.9821     0.6947   30.20   <2e-16 ***
# xtrans       11.8616     0.2165   54.79   <2e-16 ***
# ---
# Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1
#
# Residual standard error: 5.01 on 422 degrees of freedom
# Multiple R-squared:  0.8767,	Adjusted R-squared:  0.8765
# F-statistic:  3002 on 1 and 422 DF,  p-value: < 2.2e-16
#

# --------------------------------------------------------------------------
# - 1/3
#
# Call:
# lm(formula = ytrans ~ xtrans, data = data_trans)
#
# Residuals:
#       Min        1Q    Median        3Q       Max
# -0.028787 -0.006144 -0.000855  0.005204  0.054208
#
# Coefficients:
#               Estimate Std. Error t value Pr(>|t|)
# (Intercept)  0.3231043  0.0014009   230.6   <2e-16 ***
# xtrans      -0.0195184  0.0004366   -44.7   <2e-16 ***
# ---
# Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1
#
# Residual standard error: 0.0101 on 422 degrees of freedom
# Multiple R-squared:  0.8257,	Adjusted R-squared:  0.8252
# F-statistic:  1998 on 1 and 422 DF,  p-value: < 2.2e-16
#
# -------------------------------------------------------------------------------

#  -2/3
#
# Call:
# lm(formula = ytrans ~ xtrans, data = data_trans)
#
# Residuals:
#       Min        1Q    Median        3Q       Max
# -0.016082 -0.003556 -0.000632  0.002936  0.036181
#
# Coefficients:
#               Estimate Std. Error t value Pr(>|t|)
# (Intercept)  0.1020813  0.0008230  124.04   <2e-16 ***
# xtrans      -0.0105038  0.0002565  -40.95   <2e-16 ***
# ---
# Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1
#
# Residual standard error: 0.005935 on 422 degrees of freedom
# Multiple R-squared:  0.799,	Adjusted R-squared:  0.7985
# F-statistic:  1677 on 1 and 422 DF,  p-value: < 2.2e-16
# ----------------------------------------------------------------------------------
# -1
#
# Call:
# lm(formula = ytrans ~ xtrans, data = data_trans)
#
# Residuals:
#        Min         1Q     Median         3Q        Max
# -0.0067724 -0.0015626 -0.0002827  0.0012726  0.0180419
#
# Coefficients:
#               Estimate Std. Error t value Pr(>|t|)
# (Intercept)  0.0317882  0.0003676   86.48   <2e-16 ***
# xtrans      -0.0042667  0.0001146  -37.24   <2e-16 ***
# ---
# Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1
#
# Residual standard error: 0.002651 on 422 degrees of freedom
# Multiple R-squared:  0.7667,	Adjusted R-squared:  0.7662
# F-statistic:  1387 on 1 and 422 DF,  p-value: < 2.2e-16
#

library(knitr)
kable(anova(fit_b_final), caption='ANOVA Table')



plot(data_trans$ytrans ~ data_trans$xtrans, main='Scatter : ytrans ~ xtrans', xlab='xtrans', ylab='ytrans', pch=20)
abline(fit_b_final, col='red', lty=3, lwd=2)
legend('topleft', legend='Estimated Regression Line', lty=3, lwd=2, col='red')