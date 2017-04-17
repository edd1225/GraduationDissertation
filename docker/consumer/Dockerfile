FROM python:2.7
ADD ./consumer /consumer
WORKDIR /consumer
RUN pip install -r requirements.txt
CMD python consumer.py