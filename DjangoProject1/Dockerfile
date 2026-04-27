# 1. Βάση: Χρησιμοποιούμε μια ελαφριά Python
FROM python:3.12-slim

# 2. Φάκελος εργασίας: Ορίζουμε πού θα μπει ο κώδικας στο container
WORKDIR /app

# 3. Εγκατάσταση βασικών για το PyTorch (πρέπει να κατεβάσει ορισμένα συστήματα αρχείων)
RUN apt-get update && apt-get install -y \
    build-essential \
    && rm -rf /var/lib/apt/lists/*

# 4. Αντιγραφή και εγκατάσταση εξαρτήσεων
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# 5. Αντιγραφή όλου του κώδικα στο container
COPY . .

# 6. Πόρτα: Λέμε στο Docker ότι η εφαρμογή τρέχει στη 8000
EXPOSE 8000

# 7. Command: Η εντολή για να ξεκινήσει το Django
CMD ["python", "manage.py", "runserver", "0.0.0.0:8000"]