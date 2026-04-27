from django.shortcuts import render
from django.http import JsonResponse
from django.apps import apps
import torch
from torchvision import transforms
import torch.nn.functional as F
from PIL import Image
from django.views.decorators.csrf import csrf_exempt

CLASS_NAMES = ['Cat', 'Dog']

def predict_image(img_file):

    config = apps.get_app_config('inference')
    model = getattr(config, 'model', None)

    img = Image.open(img_file).convert('RGB')

    transform = transforms.Compose([
        transforms.Resize((64, 64)),
        transforms.ToTensor(),
        transforms.Normalize((0.5, 0.5, 0.5), (0.5, 0.5, 0.5))
    ])
    input_tensor = transform(img).unsqueeze(0)

    with torch.no_grad():
        output = model(input_tensor)
        probabilities = F.softmax(output, dim=1)
        confidence, predicted_index = torch.max(probabilities, dim=1)

    return CLASS_NAMES[predicted_index.item()], round(float(confidence.item()), 2)

@csrf_exempt
def prediction_api(request):
    if request.method != 'POST':
        return JsonResponse({"error": "Invalid request"}, status=400)

    pred_class, conf = predict_image(request.FILES['image'])

    return JsonResponse({
         "predicted_class": pred_class,
         "confidence": conf
    })

def home_view(request):
    context={}
    if request.method =='POST' and 'image' in request.FILES:
        pred_class, conf = predict_image(request.FILES['image'])
        context['predicted_class'] = pred_class
        context['confidence'] = conf

    return render(request, 'home.html', context)








