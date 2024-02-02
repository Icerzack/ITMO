import { Form } from 'react-bootstrap';
import Button from 'react-bootstrap/Button';
import { useState } from 'react';
import { addRouteS2 } from '../../utils/apiInteraction';
import toast from 'react-hot-toast';
import get from 'lodash.get';
import { useSetRecoilState } from 'recoil';
import { isDataNeedsToBeUpdatedState } from '../../state/atoms';

export const AddRoute = () => {
  const [fromId, setFromId] = useState('');
  const [toId, setToId] = useState('');
  const setIsDataNeedsToBeUpdated = useSetRecoilState(isDataNeedsToBeUpdatedState);

  const addNewRoute = () => {
    toast
      .promise(addRouteS2(fromId, toId), {
        loading: 'Добавляем...',
        success: () => 'Route успешно добавлено!',
        error: (err) => get(err, 'response.data.message', 'Error, проверьте поля!')
      })
      .then(() => {
        setIsDataNeedsToBeUpdated(true);
      });
  };

  return (
    <Form>
      <div className="optitle mt-3">Добавить дорогу между двумя локациями по их ID</div>
      <div className="horizontal-placer">
        <Form.Control
          type="number"
          min="1"
          value={fromId}
          placeholder="ID первой локации"
          className="form"
          onChange={(event) => setFromId(event.target.value)}
        />
        <Form.Control
          type="number"
          min="1"
          value={toId}
          placeholder="ID второй локации"
          className="form"
          onChange={(event) => setToId(event.target.value)}
        />
        <Button variant="dark" className="button-rev-1 form" onClick={addNewRoute}>
          Добавить
        </Button>
      </div>
    </Form>
  );
};
