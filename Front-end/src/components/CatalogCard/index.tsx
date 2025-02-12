import "./styles.css";
import { Product } from "../../types/product";
import { Link } from "react-router-dom";

type Props = {
  product: Product;
};

export default function CatalogCard({ product }: Props) {
  return (
    <Link to={`/product-details/${product.id}`}>
      <div className="dsc-card">
        <div className="dsc-catalog-card-top dsc-line-bottom">
          <img src={product.imgUrl} alt={product.name} />
        </div>
        <div className="dsc-catalog-card-bottom">
          <h3>R$ {product.price.toFixed(2)}</h3>
          <div className="dsc-shipping-info">
            <span className="dsc-free-shipping">
              Frete grátis <i className="fa-solid fa-bolt"></i> FULL
            </span>
          </div>
          <h4>{product.name}</h4>
          <div className="dsc-stars">
            <i className="fa-solid fa-star"></i>
            <i className="fa-solid fa-star"></i>
            <i className="fa-solid fa-star"></i>
            <i className="fa-solid fa-star"></i>
            <i className="fa-solid fa-star"></i>
          </div>
        </div>
      </div>
    </Link>
  );
}
