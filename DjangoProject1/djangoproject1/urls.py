from django.contrib import admin
from django.urls import path
from inference.views import prediction_api, home_view

urlpatterns = [
    path('predict/', prediction_api, name='predict'),
    path('',home_view, name='home')
]
