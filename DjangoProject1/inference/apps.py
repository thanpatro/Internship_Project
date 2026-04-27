from django.apps import AppConfig
from pathlib import Path
import torch
from .model import Net


class InferenceConfig(AppConfig):
    name = 'inference'
    model = None

    def ready(self):
        self.model = Net()

        model_path = Path(__file__).resolve().parent.parent / 'model.pth'

        if model_path.exists():
            state_dict = torch.load(model_path, map_location='cpu')
            self.model.load_state_dict(state_dict)

            self.model.eval()
            print("Model loaded!")
        else:
            print("Model file not found!")
