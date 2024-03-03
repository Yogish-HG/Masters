import React from "react";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Typography from "@mui/material/Typography";
import { CardActionArea } from "@mui/material";
import { useNavigate } from "react-router-dom";

const DashboardCard = ({ title, subtitle, imageName, lookerUrl, linkPath }) => {
  const navigate = useNavigate();
  const lookerRoute = `/looker-view/${linkPath}`;

  const handleCardClick = () => {
    navigate(lookerRoute, { state: { url: lookerUrl } });
  };

  return (
    <Card sx={{ maxWidth: 400 }}>
      <CardActionArea onClick={handleCardClick}>
        <CardMedia
          component="img"
          height="200"
          image={`/images/cards/${imageName}`}
        />
        <CardContent>
          <Typography gutterBottom variant="h5" component="div">
            {title}
          </Typography>
          <Typography variant="body2" color="text.secondary">
            {subtitle}
          </Typography>
        </CardContent>
      </CardActionArea>
    </Card>
  );
};

export default DashboardCard;
