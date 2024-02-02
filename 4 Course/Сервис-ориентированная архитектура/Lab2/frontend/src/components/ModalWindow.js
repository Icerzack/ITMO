import React from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import { useRecoilState, useSetRecoilState } from 'recoil';
import {
  bufferRoute,
  feedbackRouteValidator,
  isDataNeedsToBeUpdatedState,
  isEditingRoute,
  showModalForm,
  wasValidated
} from '../state/atoms';
import { RouteForm } from './RouteForm';
import { validate } from '../utils/routeValidator';
import { postRoute, putRoute } from '../utils/apiInteraction';
import toast from 'react-hot-toast';
import get from 'lodash.get';

export const ModalWindow = () => {
  const [show, setShow] = useRecoilState(showModalForm);
  const [route, setRoute] = useRecoilState(bufferRoute);
  const [isEditing, setIsEditing] = useRecoilState(isEditingRoute);
  const setFeedback = useSetRecoilState(feedbackRouteValidator);
  const setValidated = useSetRecoilState(wasValidated);
  const setIsDataNeedsToBeUpdated = useSetRecoilState(isDataNeedsToBeUpdatedState);

  const addRoute = () => {
    const freshFeedback = validate(route);
    setFeedback(freshFeedback);
    if (Object.keys(freshFeedback).length === 0) {
      toast
        .promise(postRoute(route), {
          loading: 'Добавляем...',
          success: 'Успешно!',
          error: (err) => get(err, 'response.data.message', 'Error')
        })
        .then(() => {
          setIsDataNeedsToBeUpdated(true);
          setShow(false);
          setValidated(false);
        });
    } else setValidated(true);
  };
  const updateRoute = () => {
    const freshFeedback = validate(route);
    setFeedback(freshFeedback);
    if (Object.keys(freshFeedback).length === 0) {
      toast
        .promise(putRoute(route), {
          loading: 'Обновляем...',
          success: 'Успешно!',
          error: (err) => get(err, 'response.data.message', 'Error')
        })
        .then(() => {
          setIsDataNeedsToBeUpdated(true);
          setShow(false);
          setValidated(false);
        });
    } else setValidated(true);
  };

  const clear = () => {
    setRoute({});
    setValidated(false);
  };

  const handleShow = () => {
    setIsEditing(false);
    setValidated(false);
    setShow(true);
  };
  return (
    <>
      <Button className="button-4" onClick={handleShow}>
        Добавить Route
      </Button>
      <Modal
        show={show}
        onHide={() => setShow(false)}
        contentClassName="bg-white text-dark"
        fullscreen={true}>
        <Modal.Header closeButton closeVariant="dark">
          <Modal.Title>Окно Route {isEditing ? 'для id: ' + route.id : ''}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <RouteForm isEditing={isEditing} />
        </Modal.Body>
        <Modal.Footer>
          <Button variant="outline-secondary text-dark" hidden={isEditing} onClick={clear}>
            Очистить
          </Button>
          <Button
            variant="outline-secondary text-dark"
            onClick={isEditing ? updateRoute : addRoute}>
            {isEditing ? 'Обновить Route' : 'Добавить Route'}
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};
