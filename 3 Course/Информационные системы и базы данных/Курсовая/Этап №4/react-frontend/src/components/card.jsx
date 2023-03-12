import * as React from "react";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import Typography from "@mui/material/Typography";
import { CardActionArea } from "@mui/material";

export default function DisplayCard(props) {
  const { title, shortDescription, onCardClick, name } = props;
  return (
    <Card
      sx={{
        m: "15px",
        width: "25%",
        flex: "1 1 250px",
        boxSizing: "border-box",
      }}
    >
      <CardActionArea name={name} onClick={onCardClick}>
        <CardContent>
          <Typography gutterBottom variant="h5" component="div">
            {title}
          </Typography>
          <Typography
            variant="body2"
            color="text.secondary"
            sx={{
              display: "-webkit-box",
              overflow: "hidden",
              WebkitBoxOrient: "vertical",
              WebkitLineClamp: 3,
            }}
          >
            {shortDescription}
          </Typography>
        </CardContent>
      </CardActionArea>
    </Card>
  );
}
