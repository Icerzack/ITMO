import { Form } from 'react-bootstrap';
import { InputField } from './InputField';
import { InputObject } from './InputObject';

export const RouteForm = () => {
  return (
    <Form>
      <InputField id="name" type="text" />
      <>
        <InputObject id="from" fields={['coordinates.x', 'coordinates.y', 'name']} />
        <InputObject id="to" fields={['coordinates.x', 'coordinates.y', 'name']} />
      </>
      <InputField id="distance" />
    </Form>
  );
};
