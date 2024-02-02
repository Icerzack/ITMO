import { Form } from 'react-bootstrap';
import Button from 'react-bootstrap/Button';
import { useState } from 'react';
import { findShortest } from '../../utils/apiInteraction';
import toast from 'react-hot-toast';
import get from 'lodash.get';

export const DijkstraForm = () => {
  const [fromId, setFromId] = useState('');
  const [toId, setToId] = useState('');
  const countDijkstra = () => {
    toast.promise(findShortest(fromId, toId), {
      loading: 'Вычисляем...',
      success: (response) =>
        response.data === -1
          ? 'Возможно, данного ID не существует, либо маршрут между этими локациями невозможен.'
          : 'Вычисленное значение: ' + response.data,
      error: (err) => get(err, 'response.data.message', 'Error, проверьте поля!')
    });
  };

  return (
    <Form>
      <div className="optitle mt-2">Найти кратчайшее расстояние между двумя локациями по их ID</div>
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
        <Button variant="dark" className="button-1 form" onClick={countDijkstra}>
          Рассчитать
        </Button>
      </div>
    </Form>
  );
};
